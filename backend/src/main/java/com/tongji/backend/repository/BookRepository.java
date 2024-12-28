package com.tongji.backend.repository;

import com.tongji.backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    // 根据 classId 和 traineeId 查找预订信息
    @Query("SELECT b FROM Book b WHERE b.classId = ?1 AND b.traineeId = ?2")
    List<Book> findByClassIdAndTraineeId(Integer classId, Integer traineeId);

    // 获取已支付的预订
    @Query("SELECT b FROM Book b WHERE b.bookStatus = 1")
    List<Book> findBookedCourses();

    // 获取已取消的预订
    @Query("SELECT b FROM Book b WHERE b.bookStatus = 2")
    List<Book> findCanceledCourses();

    Book findByClassId(Integer classId);

    @Query("SELECT b FROM Book b WHERE b.classId = ?1 AND b.bookStatus = ?2")
    Book findByClassIdAndStatus(Integer classID, int i);
}
