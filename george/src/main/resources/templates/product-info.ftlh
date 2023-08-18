<!DOCTYPE html>
<html>
<head>
    <title>BY sell</title>
</head>
<body>
<h1>By sell</h1><hr>
<h4>Подробная информация о товаре</h4>
<b>Название товара </b>${product.title}<br>
<b>Описание товара </b>${product.description}<br>
<b> Цена </b>${product.price}<br>
<b> Город </b>${product.city}<br>
<b> Автор </b>${product.author}<br>
<hr>
<h3>Создать новый продукт</h3>
<form action="product/delete/${product.id}" method="post">
    <input type="submit" value="Delete"/>
</form>
</body>
</html>