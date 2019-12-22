package cn.wooyulin.reportplatform.dao;

import cn.wooyulin.reportplatform.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitDao extends JpaRepository<Unit, Integer> {

    //根据id查询用户
    @Query(value = "SELECT * FROM usertest WHERE id=?",
            nativeQuery = true)
    Unit findUserById(int id);
}
