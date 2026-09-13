package com.airtribe.SmartParkingLotSystem.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import com.airtribe.SmartParkingLotSystem.entity.ParkingFloor;
import com.airtribe.SmartParkingLotSystem.entity.ParkingSpot;
import com.airtribe.SmartParkingLotSystem.entity.SpotSize;

@Component
public class ParkingIdGenerator implements IdentifierGenerator {

    private final ConcurrentMap<String, AtomicInteger> counters = new ConcurrentHashMap<>();

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        ParkingSpot spot = (ParkingSpot)object;
        String key = spot.getFloorNumber().getNumber() + "-" + spot.getSize().toString().substring(0,2);
        
        int sequence = counters.computeIfAbsent(key, ignored -> new AtomicInteger()).incrementAndGet();
        return key + "-" + sequence;
    }

 
}