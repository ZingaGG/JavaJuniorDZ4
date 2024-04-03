import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

//* 1. Создать сущность Student с полями:
//1.1 id - int
//1.2 firstName - string
//1.3 secondName - string (исключил это, так как нигде не используется + читабельность) !!!
//1.4 age - int
//2. Подключить hibernate. Реализовать простые запросы: Find(by id), Persist, Merge, Remove
//3. Попробовать написать запрос поиска всех студентов старше 20 лет (session.createQuery)

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure();
        try(SessionFactory sessionFactory = configuration.buildSessionFactory()){

            insertAction(sessionFactory); // Persist

            findByID(sessionFactory, 1); // Find(by id)

            Student student = mergeAction(sessionFactory); // Merge

            removeAction(sessionFactory, student); // Remove

            findOlderThanTwenty(sessionFactory); // Запрос поиска всех студентов больше 20

        }


    }

    private static void findOlderThanTwenty(SessionFactory sessionFactory) {
        try(Session session = sessionFactory.openSession()){
            Query<Student> studentQuery = session.createQuery("from Student where age > :age", Student.class);
            studentQuery.setParameter("age", 20L);
            List<Student> studentList = studentQuery.list();
            System.out.println("Студенты которым больше 20: ");
            System.out.println(studentList);
        }
    }

    private static void removeAction(SessionFactory sessionFactory, Student student) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();

            session.remove(student);

            tx.commit();

            Query<Student> query = session.createQuery("from Student", Student.class);
            List<Student> list = query.list();
            System.out.println("Результат удаления: ");
            System.out.println(list);
        }
    }

    private static Student mergeAction(SessionFactory sessionFactory) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();

            Student student = new Student();
            student.setId(11L);
            student.setFirstName("Student 11");
            student.setAge(22);

            session.persist(student); // Переводим студента в persistent

            tx.commit();

            findByID(sessionFactory, 11); // Смотрим на него

            student.setFirstName("Merged Student 11"); // Меняем имя

            Transaction tx1 = session.beginTransaction();

            Student result = session.merge(student);

            tx1.commit();

            findByID(sessionFactory, 11); // Смотрим на измененного

            return result;
        }
    }

    private static void findByID(SessionFactory sessionFactory, long id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            System.out.println();
            System.out.println();
            System.out.println("Найденный студент: ");
            System.out.println(session.find(Student.class, id));

            tx.commit();
        }
    }

    private static void insertAction(SessionFactory sessionFactory) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            for (long i = 1; i < 11; i++) {
                Student student = new Student();
                student.setId(i);
                student.setFirstName("Student " + i);
                student.setAge((int) (16 + i));

                session.persist(student);
            }

            tx.commit();
        }
    }
}
