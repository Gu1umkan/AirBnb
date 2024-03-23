package airbnb.repository;

import airbnb.entities.Block;
import airbnb.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
    default Block getBlockById(Long blockId){
        return findById(blockId).orElseThrow(()->new NotFoundException("Block with id:"+blockId+" not found!"));
    }
}