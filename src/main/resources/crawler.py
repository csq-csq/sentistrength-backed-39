import os.path
import pandas as pd
import requests
import time
import threading
import sys
import json

base_url = 'https://api.github.com'
owner = 'apache'
repo = 'echarts'
headers = {
    'Accept': 'application/vnd.github.v3+json',
    'Authorization': 'token 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',
    'User-Agent': 'Mozilla/5.0',
    'Content-Type': 'application/json',
    'method': 'GET',
}
proxies = {
    "http": None,
    "https": None
}
data_root = "data/"

class writeThread(threading.Thread):
    def __init__(self, issues, res_path):
        threading.Thread.__init__(self)
        self.issues = issues
        self.res_path = res_path

    def run(self):
        write_issues_to_csv_files(self.issues, self.res_path)


def compare_time(time1, time2):
    s_time = time.mktime(time.strptime(time1, '%Y-%m-%d'))
    e_time = time.mktime(time.strptime(time2, '%Y-%m-%d'))
    return int(s_time) - int(e_time)


def belong_target_version(created_time, version_dates):
    for i in range(0, len(version_dates)):
        if (compare_time(created_time, version_dates[i][0]) > 0) and (
                compare_time(version_dates[i][1], created_time) > 0):
            return i
    return -1


def get_issues(version_dates):
    max_date = version_dates[0][1]
    min_date = version_dates[0][0]
    for version_date in version_dates:
        if compare_time(version_date[1], max_date) > 0:
            max_date = version_date[1]
        if compare_time(min_date, version_date[0]) > 0:
            min_date = version_date[0]

    url = f'{base_url}/repos/{owner}/{repo}/issues'
    params = {'state': 'all', 'per_page': 100}
    page = 1
    issue_list = []
    issues_num = []
    for i in range(0, len(version_dates)):
        issues_num.append(0)
        issue_list.append([])

    while True:
        params['page'] = page
        response = requests.get(url, params=params, headers=headers, proxies=proxies)

        if response.status_code != 200:
            print(f'Failed to retrieve issues (page {page}): {response.status_code}')
            break

        page_issues = response.json()
        if not page_issues:
            break

        for issue in page_issues:
            issue_date = issue["created_at"].split('T')[0]
            index = belong_target_version(issue_date, version_dates)
            if index != -1:
                issue_list[index].append(issue)
                issues_num[index] += 1
            else:
                if compare_time(issue_date, min_date) < 0:
                    print(issues_num)
                    return issue_list
        page += 1
    print(issues_num)
    return issue_list


def get_issue_comments(issue_number):
    url = f'{base_url}/repos/{owner}/{repo}/issues/{issue_number}/comments'
    params = {'per_page': 100}
    page = 1
    comments = []
    while True:
        params['page'] = page
        response = requests.get(url, params=params, headers=headers, proxies=proxies)

        if response.status_code != 200:
            print(f'Failed to retrieve comments of issue {issue_number} (page {page}): {response.status_code}')
            break

        page_comments = response.json()
        if not page_comments:
            break
        comments.extend(page_comments)
        page += 1
    return comments


def get_issue_events(issue_number):
    url = f'{base_url}/repos/{owner}/{repo}/issues/{issue_number}/events'
    params = {'per_page': 100}
    page = 1
    events = []
    while True:
        params['page'] = page
        response = requests.get(url, params=params, headers=headers, proxies=proxies)

        if response.status_code != 200:
            print(f'Failed to retrieve events of issue {issue_number} (page {page}): {response.status_code}')
            break

        page_events = response.json()
        if not page_events:
            break
        events.extend(page_events)
        page += 1
    return events


def write_issues_to_csv_files(issues, version_path):
    print("Starting to write to files!")
    res_path = version_path + "/issues.csv"
    if not os.path.exists(res_path):
        df = pd.DataFrame(issues)
        df.to_csv(res_path, mode='a', index=False)
    else:
        df = pd.read_csv(res_path)
        df = df.append(issues, ignore_index=True)
        df.to_csv(res_path, mode='w', index=False)
    print(f"Finished writing issues to {res_path}!")


def process_issues(issues, version_dates):
    for i in range(0, len(issues)):
        if not os.path.exists(data_root + version_dates[i][0] + "_" + version_dates[i][1]):
            os.makedirs(data_root + version_dates[i][0] + "_" + version_dates[i][1])
        threads = []
        for issue in issues[i]:
            issue['comments'] = get_issue_comments(issue['number'])
            issue['events'] = get_issue_events(issue['number'])
            threads.append(writeThread(issue, data_root + version_dates[i][0] + "_" + version_dates[i][1]))
        for t in threads:
            t.start()
        for t in threads:
            t.join()


def main(date_input):
    version_dates = json.loads(date_input) # 把字符串转换为列表
    issues = get_issues(version_dates)
    process_issues(issues, version_dates)


if __name__ == "__main__":
    main(sys.argv[1])  # sys.argv[1] 是命令行的第一个参数
