package facebook.xml;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XmlRepository extends JpaRepository<Xml,Long> {
}
