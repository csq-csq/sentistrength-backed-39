import os.path
import pandas as pd
import requests
import time
import threading
import argparse
# 设置GitHub API的基本URL和相关参数
base_url = 'https://api.github.com'
owner = ''
repo = ''
version_dates = []
headers = {
    'Accept': 'application/vnd.github.v3+json',
    'Authorization': 'token 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',
    'User-Agent': 'Mozilla/5.0',
    'Content-Type': 'application/json',
    'method': 'GET',
}

# 设置代理
proxies = {
    "http": None,
    "https": None
}

# 设置结果文件路径
data_root = "/home/SE3/sentiSpring/data/"
res_dirs = []


# 为线程定义一个函数
class writeThread(threading.Thread):
    def __init__(self, issues, res_path):
        threading.Thread.__init__(self)
        self.issues = issues
        self.res_path = res_path

    def run(self):
        write_issues_to_csv_files(self.issues, self.res_path)


# 比较两个时间字符串的大小
def compare_time(time1, time2):
    s_time = time.mktime(time.strptime(time1, '%Y-%m-%d'))
    e_time = time.mktime(time.strptime(time2, '%Y-%m-%d'))
    return int(s_time) - int(e_time)


# 返回所属的版本序号
def belong_target_version(created_time):
    for i in range(0, len(version_dates)):
        if (compare_time(created_time, version_dates[i][0]) > 0) and (
                compare_time(version_dates[i][1], created_time) > 0):
            return i
    return -1


# 获取特定版本的issue
def get_issues():
    # 对比获取最大时间和最小时间
    max_date = version_dates[0][1]
    min_date = version_dates[0][0]
    for version_date in version_dates:
        if compare_time(version_date[1], max_date) > 0:
            max_date = version_date[1]
        if compare_time(min_date, version_date[0]) > 0:
            min_date = version_date[0]

    # 初始化相关参数、空数组
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

        # 通过 issue 创建时间与版本更换时间筛选
        for issue in page_issues:
            issue_date = issue["created_at"].split('T')[0]
            index = belong_target_version(issue_date)
            if index != -1:
                issue_list[index].append(issue)
                issues_num[index] += 1
            else:
                if compare_time(issue_date, min_date) < 0:
                    print(issues_num)
                    return issue_list
        page += 1

        # test code
        # if page > 3:
        # for issue in page_issues:
        # print(issue['labels'])
        # break

    print(issues_num)
    return issue_list


# 获取特定issue的评论
def get_issue_comments(issue_number):
    url = f'{base_url}/repos/{owner}/{repo}/issues/{issue_number}/comments'
    response = requests.get(url, headers=headers)

    if response.status_code != 200:
        print(f'Failed to retrieve comments for issue {issue_number}: {response.status_code}')
        return []

    comment_id_list = []
    user_id_list = []
    created_time_list = []
    updated_time_list = []
    body_list = []

    comments = response.json()
    resp_format = "> "
    for comment in comments:
        # 处理多余的回车
        text = comment['body'].replace('\r\n', ' ')

        # 去除回复的原评论内容(由于空格不一致问题，先拆成数组再重新组合)
        if resp_format in text:
            text = text.replace(resp_format, ' ')
            for comment_body in body_list:
                text = remove_str_ignore_spaces(comment_body, text)

        # 存入数组
        comment_id_list.append(comment['id'])
        user_id_list.append(comment['user']['id'])
        created_time_list.append(comment['created_at'])
        updated_time_list.append(comment['updated_at'])
        body_list.append(text)

    # 添加延迟，暂停0.5秒钟
    # time.sleep(0.5)
    return comment_id_list, user_id_list, created_time_list, updated_time_list, body_list


def remove_empty_str(arr):
    while '' in arr:
        arr.remove('')
    return arr


# def equal_ignore_some(str1, str2):
#     s1 = str1.replace(" ", "").replace("`", "")
#     s2 = str2.replace(" ", "").replace("`", "")
#     return s1 in s2


# 忽略空格删除重复的字符串
def remove_str_ignore_spaces(str1, str2):
    remove_str_arr = remove_empty_str(str1.split(" "))
    target_str_arr = remove_empty_str(str2.split(" "))
    remove_len = len(remove_str_arr)
    # print(str(remove_len) + " " + remove_str)
    # print(str(len(target_str_arr)) + " " + target)
    for i in range(len(target_str_arr)):
        if target_str_arr[i] == remove_str_arr[0] and i + remove_len < len(target_str_arr):
            index = i
            same_num = 1
            for j in range(1, remove_len):
                if target_str_arr[j + index] == remove_str_arr[j]:
                    same_num += 1
                if same_num - j > 2:
                    break
            # 设置误差
            # print(remove_len-same_num)
            # print(remove_len)
            if remove_len - same_num == 0 or (remove_len - same_num <= 1 and remove_len > 10):
                # print("yes"+"\n")
                del target_str_arr[index:index + remove_len]
                # print(" ".join(remove_str_arr)+"\n"+" ".join(target_str_arr)+"\n")
                return " ".join(remove_empty_str(target_str_arr))
    return str2


# 将每个issue分别写入一个文件
def write_issues_to_csv_files(issues, res_path):
    # 开始写入文件
    for issue in issues:
        comment_id_list = []
        user_id_list = []
        created_time_list = []
        updated_time_list = []
        body_list = []

        # 存入 issue 各项信息
        issue_number = issue['number']
        issue_body = issue['body']
        if issue_body is not None:
            issue_body = issue_body.replace("\r\n", ' ').replace("\n", ' ')
        comment_id_list.append(-1)
        user_id_list.append(issue['user']['id'])
        created_time_list.append(issue['created_at'])
        updated_time_list.append(issue['updated_at'])
        body_list.append(issue_body)

        # 存入 comment 对应信息
        comment_id_list1, user_id_list1, created_time_list1, updated_time_list1, body_list1 \
            = get_issue_comments(issue_number)
        comment_id_list = comment_id_list + comment_id_list1
        user_id_list = user_id_list + user_id_list1
        created_time_list = created_time_list + created_time_list1
        updated_time_list = updated_time_list + updated_time_list1
        body_list = body_list + body_list1

        # 写入 csv 文件
        file_name = f'issue_{issue_number}.csv'
        dataframe = pd.DataFrame({'comment_id': comment_id_list, 'user_id': user_id_list,
                                  'created_time': created_time_list, 'updated_time': updated_time_list,
                                  'body': body_list})
        dataframe.to_csv(res_path + file_name, index=False, sep=',')


# 爬虫主方法
def crawl_target(owner_name="apache", repo_name="echarts", version_date_list=None):
    # 设置默认实参
    if version_date_list is None:
        version_date_list = [['2022-06-14', '2022-09-24'],
                             ['2022-09-25', '2022-12-08']
                             ['2022-12-09', '2023-03-23'],
                             ]
    # 获取参数
    global owner, repo, version_dates, res_dirs
    owner = owner_name
    repo = repo_name
    version_dates = version_date_list
    res_dirs = []
    for i in range(0, len(version_dates)):
        res_dirs.append(str(i) + "/")

    # 开始爬取
    start_crawl = time.time()
    version_issues = get_issues()
    print("Success Get Issues!")
    end_crawl = time.time()
    crawl_total_time = end_crawl - start_crawl
    print(f"爬取耗时：{crawl_total_time}秒")

    # 将结果写入不同文件
    # 检查数据存储的根目录是否存在
    if not os.path.exists(data_root):
        os.mkdir(data_root)

    # 创建线程池，用于获取评论和写入文件
    threads = []
    start_write = time.time()

    # 多线程写入
    for version_index in range(0, len(version_issues)):
        # 将每个版本的 issue 分裂为最多含100个元素小列表
        issues_list = [version_issues[version_index][i:i + 50] for i in
                       range(0, len(version_issues[version_index]), 50)]
        for j in range(0, len(issues_list)):
            # 创建新线程
            res_path = os.path.join(data_root, res_dirs[version_index])
            if not os.path.exists(res_path):
                os.makedirs(res_path)
            thread = writeThread(issues_list[j], res_path)
            # 开启新线程
            thread.start()
            # 添加新线程到线程列表
            threads.append(thread)

    # 等待所有线程完成
    for thread in threads:
        thread.join()

    print("Success Write Data!")
    end_write = time.time()
    write_total_time = end_write - start_write
    print(f"写入耗时：{write_total_time}秒")


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Crawl target GitHub repository.')
    parser.add_argument('--version_dates', nargs="+", default=['2022-06-14', '2022-09-24', '2022-09-25', '2022-12-08', '2022-12-09', '2023-03-23'],
                        help='Version dates for the GitHub repository in pairs.')

    args = parser.parse_args()

    # 由于输入的是一维列表，我们需要将其分解为二维列表
    version_dates = [args.version_dates[i:i + 2] for i in range(0, len(args.version_dates), 2)]

    crawl_target(owner_name="apache", repo_name="echarts", version_date_list=version_dates)


