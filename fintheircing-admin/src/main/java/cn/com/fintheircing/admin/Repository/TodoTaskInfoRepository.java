package cn.com.fintheircing.admin.Repository;

import cn.com.fintheircing.admin.todotask.entity.TodoTaskInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoTaskInfoRepository extends JpaRepository<TodoTaskInfo,String> {
}
