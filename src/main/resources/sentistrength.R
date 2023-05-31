library("rjson")
library("ggplot2")
library("stringr")
#library("ggpubr")
#library("corrplot")
#library("grid")
#library("gridExtra")
library(readr)
library(data.table)
library(dplyr)

dir <- "/home/SE3/senti/data/"
res_dir<-"/home/SE3/senti/result/"
folder_list = list.files(path = dir,recursive = FALSE,full.names = TRUE)
for(j in 1:length(folder_list)){
  dir<-folder_list[j]
  file_list = list.files(path = dir, pattern = "*.csv$",recursive = TRUE,full.names = TRUE)
  name_list = list.files(path = dir, pattern = "*.csv$",recursive = TRUE,full.names = FALSE)
  store_csv = paste(res_dir,paste(substr(dir,11,nchar(dir)),".csv",sep=""),sep ="")
  store_txt = paste(res_dir,paste(substr(dir,11,nchar(dir)),".txt",sep=""),sep="")
  
  # created vector with 5 characters
  columns= c("issue_id","comment_id","user_id","created_time","updated_time","body")
  
  # pass this vector length to ncol parameter
  # and nrow with 0
  myData = data.frame(matrix(nrow = 0, ncol = length(columns)))
  
  # assign column names
  colnames(myData) = columns
  
  # display
  #print(myData)
  
  
  for(i in 1:length(file_list))     #ѭ�����Ե�ַ���б�
  {
    data = fread(file = file_list[i],encoding = 'UTF-8')#��ȡcsv�ļ�
    data <- data %>%
      mutate(issue_id=substr(name_list[i],1,nchar(name_list[i])-4),
             .before=comment_id)
    for(i in 0:length(data$body)){
      data$body[i]<-str_replace_all(data$body[i],"\n","");
      data$body[i]<-str_replace_all(data$body[i],"\t","");
      data$body[i]<-str_replace_all(data$body[i],"#","");
      data$body[i]<-str_replace_all(data$body[i],"```.+```","");
      data$body[i]<-str_replace_all(data$body[i],"!\\[image\\]\\(.+\\)","");
      data$body[i]<-str_replace_all(data$body[i],"\\[.+\\]\\(.+\\)","");
      data$body[i]<-str_replace_all(data$body[i],"<!--.+-->","");
      data$body[i]<-str_replace_all(data$body[i],"<img.+>","");
      data$body[i]<-str_replace_all(data$body[i],"<img.+\"\"","");
      #print(data$body[i])
    }
    myData<-rbind(myData,data);
    #���������csv�ļ��򴴽���׷��д����csv�ļ�
    #write_csv(data,path = store_csv,append = TRUE, col_names = FALSE)
    #write.table(data,file = store_tsv, sep="\t",append = TRUE, row.names = FALSE,col.names = FALSE)
    #write.table(data, file = store_txt,append = TRUE, row.names = FALSE,col.names = FALSE)
    #write.table(data$body, file = "F:/R/res/534version_text.txt",append = TRUE, row.names = FALSE,col.names = FALSE,quote =FALSE)
    #write.table(data, file = "F:/R/res/534version_tsv.txt",sep="\t",append = TRUE, row.names = FALSE,col.names = FALSE,quote =FALSE)
  }
  
  myData=myData[!str_detect(myData$body,"[\\p{Han}]")]
  
  write_csv(myData,file = store_csv,append = FALSE, col_names = FALSE)
  #write.table(myData$body, file = "F:/R/res/534version_text.txt",append = FALSE, row.names = FALSE,col.names = FALSE,quote =FALSE)
  write.table(myData, file = store_txt,sep="\t",append = FALSE, row.names = FALSE,col.names = FALSE,quote =FALSE)
  
}



#data <- read.csv(path)
#print(data)
#print(length(data$body))
#data <- data %>%
#  mutate(issue_id="issue_17217",
#         .before=comment_id)

#for(i in 0:length(data$body)){
#  data$body[i]<-str_replace_all(data$body[i],"\n","");
#  print(data$body[i])
#}

#print(data)
#write.csv(data,"F:/R/res/test114514.csv")

