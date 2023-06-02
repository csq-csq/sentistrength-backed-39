import pandas as pd
import matplotlib.pyplot as plt
import os

folder_path = "/home/SE3/sentiSpring/proresult"

version_emotions = []
for filename in os.listdir(folder_path):
    if filename.endswith(".csv"):
        df = pd.read_csv(os.path.join(folder_path, filename))
        positive_count = (df['trinary'] > 0).sum()
        negative_count = (df['trinary'] < 0).sum()
        neutral_count = (df['trinary'] == 0).sum()
        total_count = df['trinary'].count()
        positive_percent = positive_count / total_count
        negative_percent = negative_count / total_count
        neutral_percent = neutral_count / total_count
        version_emotions.append({
            'version': filename.replace('.csv', ''),
            'positive_percent': positive_percent,
            'negative_percent': negative_percent,
            'neutral_percent': neutral_percent
        })

version_emotions_df = pd.DataFrame(version_emotions)
version_emotions_df = version_emotions_df.sort_values('version')

plt.figure(figsize=(12, 8))
plt.grid(True)

plt.plot(version_emotions_df['version'], version_emotions_df['positive_percent'], label='positive', linewidth=2, linestyle='-')
plt.plot(version_emotions_df['version'], version_emotions_df['negative_percent'], label='negative', linewidth=2, linestyle=':')
plt.plot(version_emotions_df['version'], version_emotions_df['neutral_percent'], label='neutral', linewidth=2, linestyle='--')

plt.xticks(rotation=45)

plt.xlabel('Version', fontsize=12)
plt.ylabel('Percent', fontsize=12)
plt.title('Emotion Percent per Version', fontsize=16)
plt.legend(loc='best', fontsize=12)

plt.tight_layout()
plt.savefig('/home/SE3/sentiSpring/pic/output.png')
#plt.show()

#plt.show()
