export function download(filename) {
    alert("开始下载")
    var url = "http://localhost:8080/api/file/download?filePath=" + filename
    window.open(url)
}