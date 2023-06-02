
import pandas as pd
import matplotlib.pyplot as plt
import os
from datetime import datetime

# 创建一个空的DataFrame来保存所有文件的数据
total_df = pd.DataFrame()

# 注意替换为你实际的csv文件夹绝对路径
csv_folder_path = '/home/SE3/sentiSpring/proresult' 

# 遍历目录下的所有文件
for filename in os.listdir(csv_folder_path):
    if filename.endswith('.csv'):
        # 读取CSV文件
        df = pd.read_csv(os.path.join(csv_folder_path, filename))

        # 提取出上传时间的小时
        df['upload_hour'] = pd.to_datetime(df['upload_date']).dt.hour

        # 将时间按照每三小时的区间进行分组
        df['time_period'] = pd.cut(df['upload_hour'], bins=[0, 3, 6, 9, 12, 15, 18, 21, 24])

        # 将处理后的数据添加到total_df中
        total_df = pd.concat([total_df, df], ignore_index=True)

# 计算各个情况类别在各个时间段的数量
emotions_counts = total_df.groupby(['time_period', 'trinary']).size().unstack(fill_value=0)

# 计算各个情况类别在各个时间段的占比
emotions_ratio = emotions_counts.div(emotions_counts.sum(axis=1), axis=0)

# 绘制折线图
plt.figure(figsize=(10, 6))
plt.plot(emotions_ratio.index.astype(str), emotions_ratio[1], label='Positive')
plt.plot(emotions_ratio.index.astype(str), emotions_ratio[0], label='Neutral')
plt.plot(emotions_ratio.index.astype(str), emotions_ratio[-1], label='Negative')
plt.xlabel('Time Period')
plt.ylabel('Ratio')
plt.title('Emotions Ratio by Time Period')
plt.legend()


plt.savefig("/home/SE3/sentiSpring/pic/timeevaluate.png")
