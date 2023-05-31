import csv
import os

# 指定文件夹路径，例如 "path/to/your/folder"
directory_path = "/home/SE3/sentiSpring"

# 遍历文件夹内所有文件
for filename in os.listdir(directory_path):
    # 确保只处理txt文件
    if filename.endswith(".txt"):
        txt_file = os.path.join(directory_path, filename)
        # 将.txt扩展名更改为.csv
        csv_file = os.path.join(directory_path, filename.replace(".txt", ".csv"))

        with open(txt_file, "r", encoding="utf-8") as in_text:
            in_reader = csv.reader(in_text, delimiter='\t')
            with open(csv_file, "w", newline='', encoding="utf-8") as out_csv:
                out_writer = csv.writer(out_csv)
                for row in in_reader:
                    out_writer.writerow(row)
