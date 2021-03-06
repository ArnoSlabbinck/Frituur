package be.thomasmore.graduaten.hellospring.services;

import be.thomasmore.graduaten.hellospring.entities.Timeslot;
import be.thomasmore.graduaten.hellospring.repositories.TimeslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class TimeslotService {

    @Autowired
    private final TimeslotRepository timeslotRepository;


    public TimeslotService(TimeslotRepository timeslotRepository) {
        this.timeslotRepository = timeslotRepository;
    }

    protected void MakeNewTimeSlot(Timeslot timeslot) {
        timeslotRepository.save(timeslot);
    }

    protected List<Timeslot> GetAllTimeSlots() {
        return timeslotRepository.findAll();
    }

    protected void DeleteTimeSlot(Long id) {
        Timeslot timeslot = timeslotRepository.getById(id);
        timeslotRepository.delete(timeslot);
    }

    protected  void DeleteAllTimeSlots() {
        timeslotRepository.deleteAll();
    }

    protected List<Timeslot> GetAllAvailableTimeslots() {
        List<Timeslot> timeslots = timeslotRepository.findAll();
        List<Timeslot> allAvailableTimeslot = new ArrayList<>();

        for (Timeslot timeslot : timeslots) {
            if(timeslot.getIsAvailable() == true){
                allAvailableTimeslot.add(timeslot);
            }
        }

        return allAvailableTimeslot;
    }

    protected boolean CheckIfTimeSlotIsAvailable(Timeslot givenTimeslot) {
        List<Timeslot> timeslots = timeslotRepository.findAll();
        //Check if the timeslot exists and is available
        for(Timeslot timeslot : timeslots) {
            if(timeslot.getIsAvailable() == true
                    && timeslot.getTimeArrival() == givenTimeslot.getTimeArrival()){
                return true;
            }
        }

        return false;
    }

    protected List<Timeslot> GetAllTimeslots() {
        return timeslotRepository.findAll();
    }

    // Update all the timeslots from the timeslot
    protected boolean UpdateTimeslot(Timeslot timeslot){
        try{
            var timeslotdb = timeslotRepository.getById(timeslot.getId());
            timeslotdb.setBeginTime(timeslot.getBeginTime());
            timeslotdb.setEndtime(timeslot.getEndtime());
            timeslotdb.setIsAvailable(timeslot.getIsAvailable());
            timeslotdb.setTimeArrival(timeslot.getTimeArrival());
            timeslotRepository.save(timeslotdb);
            return true;

        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    protected Timeslot GetTimeSlotById(Long id) {
        return timeslotRepository.getById(id);
    }
}
