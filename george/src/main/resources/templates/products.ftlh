<!DOCTYPE html>
<html>
<head>
    <title>Поиск кавалеров</title>
</head>
<body>
<h1>Поиск</h1>
<h4>по номеру</h4>
<form action="/" method="get">
    Поиск по названию:<input type="text" name="title"><br>
    <input type=submit value="Поиск"/>
</form>
<#list products as product>
    <div>
        <p><b>${product.title}</b> ${product.price}.rub|<a href="/product/${product.id}">Подробнее</a></p>
    </div>
<#else>
    <h3>Товаров нет</h3>
    </div>
</#list>
<hr>
<h3>Создать новый продукт</h3>
<form action="product/create" method="post">
    Название объявления :<input type="text" name="title"/><br><br>
    Описание объявления :<input type="text" name="description"/><br><br>
    Цена :<input type="number" name="price"/><br><br>
    Город :<input type="text" name="city"/><br><br>
    Имя :<input type="text" name="author"/><br><br>
    Добавить товар
    <input type="submit" value="Добавить товар"/>

</form>
</body>
</html>