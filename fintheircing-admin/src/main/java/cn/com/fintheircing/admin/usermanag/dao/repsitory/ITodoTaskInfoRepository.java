package cn.com.fintheircing.admin.usermanag.dao.repsitory;

import cn.com.fintheircing.admin.todotask.entity.TodoTaskInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITodoTaskInfoRepository extends JpaRepository<TodoTaskInfo,String> {
}
