export function download(filename) {
    alert("开始下载")
    var url = "http://8.130.116.36/api/file/download?filePath=" + filename
    window.open(url)
}