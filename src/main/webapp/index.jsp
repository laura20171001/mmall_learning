<html>
<body>
<h2>Hello World!</h2>
springmvc upload files
<form name="form1" action="/manage/product/upload.do"  method = "post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="springmvc_upload_file" />
</form>

richtext upload files
<form name="form1" action="/manage/product/richtext_img_upload.do"  method = "post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="srichtext_img_upload_file" />
</form>

</body>
</html>
