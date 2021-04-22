package fx.fx.repository;
import fx.fx.classes.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * TransactionRepository.java
 *
 * The repository that stores all of the Transaction objects.
 * Provides ability to find transactions by date and id.
 * All entries in the repository can be seen at /transactionlist
 * @author Yosif Gorelyov
 * @date 20/04/2021
 */
@RepositoryRestResource(collectionResourceRel = "transactionlist", path = "transactionlist")
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

  ///In order to have paging, a Pageable object needs to be initialized.
  Page<Transaction> findBydate(@Param("date") String date, Pageable pageable);
  Page<Transaction> findByid(@Param("id") Long id, Pageable pageable);
}