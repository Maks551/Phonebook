call mvn -DskipTests=true clean package
call java -DDATABASE_URL="mysql://root:root@localhost:3306/phone_books" target/*.war
