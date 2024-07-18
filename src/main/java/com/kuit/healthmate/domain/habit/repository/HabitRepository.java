package com.kuit.healthmate.domain.habit.repository;

import com.kuit.healthmate.domain.habit.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository  extends JpaRepository<Habit, Long> {
    @Query("SELECT h FROM Habit h WHERE h.user.id = :userId AND h.status = 'ACTIVE' AND SUBSTRING(h.selectedDay, :dayOfWeek, 1) = '1'")
    List<Habit> findActiveHabitsByUserIdAndDayOfWeek(@Param("userId") Long userId, @Param("dayOfWeek") int dayOfWeek);
}
