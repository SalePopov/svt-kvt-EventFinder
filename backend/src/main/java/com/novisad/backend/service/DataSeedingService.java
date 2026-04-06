package com.novisad.backend.service;

import com.novisad.backend.model.Event;
import com.novisad.backend.model.Location;
import com.novisad.backend.model.Role;
import com.novisad.backend.model.User;
import com.novisad.backend.repository.EventRepository;
import com.novisad.backend.repository.LocationRepository;
import com.novisad.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DataSeedingService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LocationRepository locationRepository; // << NOVO
    private final EventRepository eventRepository;

    public DataSeedingService(UserRepository userRepository, PasswordEncoder passwordEncoder, LocationRepository locationRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.locationRepository = locationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setCreatedAt(java.time.LocalDate.now());
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);

        }

        if (locationRepository.count() == 0) {
            System.out.println(">>> Kreiranje test podataka za Mesta i Događaje... <<<");

            Location ck13 = new Location();
            ck13.setName("CK13 Crna Kuća");
            ck13.setDescription("Omladinski centar CK13, poznat i kao Crna kuća, je nastao kao rezultat potrebe za postojanjem nezavisnog prostora u Novom Sadu.");
            ck13.setAddress("Vojvode Bojovića 13");
            ck13.setType("Kulturni Centar");
            ck13.setCreatedAt(LocalDate.now());
            ck13.setTotalRating(0.0);
            locationRepository.save(ck13);

            Location domKulture = new Location();
            domKulture.setName("Dom Kulture NS");
            domKulture.setDescription("Mesto za koncerte, predstave i kulturna dešavanja u srcu grada.");
            domKulture.setAddress("Ignjata Pavlasa 4");
            domKulture.setType("Klub");
            domKulture.setCreatedAt(LocalDate.now());
            domKulture.setTotalRating(0.0);
            locationRepository.save(domKulture);


            Event event1 = new Event();
            event1.setName("Veče društvenih igara");
            event1.setAddress(ck13.getAddress());
            event1.setType("Druženje");
            event1.setDate(LocalDate.now());
            event1.setRecurrent(true);
            event1.setPrice(null);
            event1.setLocation(ck13);
            eventRepository.save(event1);

            Event event2 = new Event();
            event2.setName("Koncert benda Repetitor");
            event2.setAddress(domKulture.getAddress());
            event2.setType("Koncert");
            event2.setDate(LocalDate.now().plusDays(10));
            event2.setRecurrent(false);
            event2.setPrice(1200.0);
            event2.setLocation(domKulture);
            eventRepository.save(event2);

            Event event3 = new Event();
            event3.setName("Projekcija filma: Blade Runner");
            event3.setAddress(ck13.getAddress());
            event3.setType("Film");
            event3.setDate(LocalDate.now().plusDays(5));
            event3.setRecurrent(false);
            event3.setPrice(300.0);
            event3.setLocation(ck13);
            eventRepository.save(event3);
        }
    }
}