package com.kuit.healthmate.domain.habit.repository;

import com.kuit.healthmate.domain.habit.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HabitRepository  extends JpaRepository<Habit, Long> {
    @Query("SELECT h FROM Habit h WHERE h.user.id = :userId AND h.status = 'ACTIVE' AND SUBSTRING(h.selectedDay, :dayOfWeek, 1) = '1'")
    List<Habit> findActiveHabitsByUserIdAndDayOfWeek(@Param("userId") Long userId, @Param("dayOfWeek") int dayOfWeek);

    @Modifying
    @Query("update Habit h set h.status = 'INACTIVE' where h.id = :habitId")
    void updateHabitStatus(@Param("habitId") Long habitId);

    @Modifying
    @Query("UPDATE Habit h SET h.name = :name, h.memo = :memo, h.updatedAt = :updatedAt, h.selectedDay = :selectedDay WHERE h.id = :habitId")
    void updateHabit(@Param("habitId") Long habitId, @Param("name") String name, @Param("memo") String memo, @Param("updatedAt")LocalDateTime updatedAt, @Param("selectedDay") String selectedDay);
}
