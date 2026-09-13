package com.airtribe.SmartParkingLotSystem.generator;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;
import com.airtribe.SmartParkingLotSystem.entity.ParkingSpot;


@Component
/**
 * Generates parking spot identifiers using the floor number, spot size, and a
 * per-floor-and-size sequence.
 *
 * <p>Generated identifiers use the format {@code floor-size-sequence}, where
 * {@code size} is the first two characters of the spot size.</p>
 */
public class ParkingIdGenerator implements IdentifierGenerator {

    private final ConcurrentMap<String, AtomicInteger> counters = new ConcurrentHashMap<>();

    /**
     * Generates an identifier for a parking spot.
     *
     * @param session current Hibernate session contract
     * @param object entity for which the identifier is being generated; it must
     *               be a {@link ParkingSpot}
     * @return generated parking spot identifier
     * @throws HibernateException if Hibernate cannot generate the identifier
     */
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        ParkingSpot spot = (ParkingSpot)object;
        String key = spot.getFloorNumber().getNumber() + "-" + spot.getSize().toString().substring(0,2);
        
        int sequence = counters.computeIfAbsent(key, ignored -> new AtomicInteger()).incrementAndGet();
        return key + "-" + sequence;
    }

 
}