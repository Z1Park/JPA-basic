package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member1 = new Member();
            member1.setName("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setName("member2");
            em.persist(member2);

            Member member3 = new Member();
            member3.setName("member3");
            em.persist(member3);

            em.flush();
            em.clear();

            Order order1 = new Order();
            order1.setStatus(OrderStatus.ORDER);
            order1.setMember(member1);
            em.persist(order1);

            Order order2 = new Order();
            order2.setStatus(OrderStatus.CANCEL);
            order2.setMember(member1);
            em.persist(order2);

            Order order3 = new Order();
            order3.setStatus(OrderStatus.ORDER);
            order3.setMember(member2);
            em.persist(order3);

            Order order4 = new Order();
            order4.setStatus(OrderStatus.CANCEL);
            order4.setMember(member3);
            em.persist(order4);

            int count = em.createQuery("update Member m set m.age = 20").executeUpdate();
            System.out.println("count = " + count);

            System.out.println("member1.getAge() = " + member1.getAge());
            System.out.println("member2.getAge() = " + member2.getAge());
            System.out.println("member3.getAge() = " + member3.getAge());

            em.clear();

            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
            for (Member member : result) {
                System.out.println("member.age = " + member.getAge());
            }


//            List<Member> members = em.createNamedQuery("Member.findByName", Member.class)
//                    .setParameter("name", "member1")
//                    .getResultList();
//
//            for (Member member : members) {
//                System.out.println("member = " + member);
//            }

//            List<Member> result = em.createQuery("select distinct m from Member m join fetch m.orders",
//                    Member.class).getResultList();
//
//            for (Member member : result) {
//                System.out.println("member = " + member + ", " + member.getOrders());
//            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
