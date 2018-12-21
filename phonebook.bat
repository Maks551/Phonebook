call mvn -DskipTests=true clean package
call java -Dspring.profiles.active="MySQL" -DDATABASE_URL="mysql://root:root@localhost:3306/phone_books" target/*.war