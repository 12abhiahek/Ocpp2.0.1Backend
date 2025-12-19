package com.ocpp201.websocket.websockets201.repository;

import com.ocpp201.websocket.websockets201.model.IdTag;

//import com.ocpp201.websocket.websockets201.model.OcppTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
//public interface IdTagRepository extends JpaRepository<IdTag, Integer> {
//    Optional<IdTag> findByIdToken(String idToken);
////Optional<OcppTag> findByIdTag(String idTag);
//}

@Repository
public interface IdTagRepository extends JpaRepository<IdTag, Long> {

    Optional<IdTag> findByIdTag(String idTag);
}