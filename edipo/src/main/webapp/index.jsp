<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="org.javalite.activejdbc.*, br.com.edipo.ada.model.entity.Usuario"
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
INDEX
<%
Base.open("java:jboss/datasources/edipo");
//Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/edipo", "root", "leaf");

Usuario u = new Usuario();
u.set("idUsuario", 1);
u.set("dsIdentificador", "denys@braga.com");
u.set("dsSenha", "nononon");
u.set("dsSal", "non");
u.set("dsNome", "Denys");
u.set("dtNascimento", "2014-10-12");
u.saveIt();
%>
</body>
</html>