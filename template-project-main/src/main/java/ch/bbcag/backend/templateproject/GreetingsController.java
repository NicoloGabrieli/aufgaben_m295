package ch.bbcag.backend.templateproject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/greetings")
public class GreetingsController {
    @GetMapping
    public ResponseEntity<?> greetings(@RequestParam(value = "name", defaultValue = "World") String name) {
        String greeting = getRandomGreeting();
        return ResponseEntity.ok(greeting + " " + name);
    }

    private static final List<String> GREETINGS = new ArrayList<>(List.of("Hallo", "Hi", "Hey", "Ciao"));

    private String getRandomGreeting() {
        Random random = new Random();
        int randomIndex = random.nextInt(GREETINGS.size());
        return GREETINGS.get(randomIndex);
    }

    @PostMapping
    public ResponseEntity<?> createGreeting(@RequestBody String greeting) {
        if (GREETINGS.contains(greeting)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Greeting already exists");
        }

        GREETINGS.add(greeting);
        return ResponseEntity.status(HttpStatus.CREATED).body(greeting);
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<?> deleteGreeting(@PathVariable int index) {
        if (index < 0 || index >= GREETINGS.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Greeting not found at index " + index);
        }

        String deletedGreeting = GREETINGS.remove(index);
        return ResponseEntity.ok("Deleted greeting: " + deletedGreeting);
    }
}
