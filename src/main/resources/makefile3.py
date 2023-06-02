import pandas as pd
import matplotlib.pyplot as plt
import os
from math import ceil
from matplotlib.gridspec import GridSpec

def calculate_emotion_intensity(row):
    emotion_sum = row['pos'] - row['neg']
    if emotion_sum > 4:
        return 'Strong'
    elif 3 <= emotion_sum <= 4:
        return 'Medium'
    else:
        return 'Low'

folder_path = "/home/SE3/sentiSpring/proresult"

emotion_categories = ['Strong', 'Medium', 'Low']

for filename in os.listdir(folder_path):
    if filename.endswith(".csv"):
        df = pd.read_csv(os.path.join(folder_path, filename))

        # Create a new column "emotion_intensity"
        df['emotion_intensity'] = df.apply(calculate_emotion_intensity, axis=1)

        # Separate df into positive, neutral, and negative dataframes
        df_positive = df[df['trinary'] > 0]
        df_neutral = df[df['trinary'] == 0]
        df_negative = df[df['trinary'] < 0]

        # Count the emotion intensity for each sentiment and ensure all categories are present
        positive_counts = df_positive['emotion_intensity'].value_counts().reindex(emotion_categories, fill_value=0)
        neutral_counts = df_neutral['emotion_intensity'].value_counts().reindex(emotion_categories, fill_value=0)
        negative_counts = df_negative['emotion_intensity'].value_counts().reindex(emotion_categories, fill_value=0)
        print(positive_counts)
        print(neutral_counts)
        # Plotting
        fig = plt.figure(figsize=(14, 6))
        gs = GridSpec(1, 3, figure=fig)

        ax1 = fig.add_subplot(gs[0, 0])
        ax2 = fig.add_subplot(gs[0, 1])
        ax3 = fig.add_subplot(gs[0, 2])

        positive_counts_filtered = positive_counts[positive_counts > 0]
        neutral_counts_filtered = neutral_counts[neutral_counts > 0]
        negative_counts_filtered = negative_counts[negative_counts > 0]

        ax1.pie(positive_counts_filtered, labels=positive_counts_filtered.index, autopct='%1.1f%%')
        ax2.pie(neutral_counts_filtered, labels=neutral_counts_filtered.index, autopct='%1.1f%%')
        ax3.pie(negative_counts_filtered, labels=negative_counts_filtered.index, autopct='%1.1f%%')

        ax1.set_title('Positive Emotions')
        ax2.set_title('Neutral Emotions')
        ax3.set_title('Negative Emotions')

        fig.suptitle('Emotion Intensity for Version ' + filename.replace('.csv', ''), fontsize=16)
        plt.tight_layout(rect=[0, 0.03, 1, 0.95])
        plt.savefig('/home/SE3/sentiSpring/pic/Emotion_Intensity_'+filename.replace('.csv', '')+'.png')
       # plt.show()
