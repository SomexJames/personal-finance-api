package org.example;

import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @GetMapping("/api/test/{resourceId}")
    public String getString(@PathVariable String resourceId) {
        return "Here is your response: " + resourceId;
    }

    @PostMapping("/api/setProfile")
    public void receiveData(@RequestBody Profile profile) {
        System.out.println("Here is the received profile: " + profile);
    }
}

class Profile {
    private String name;
    private String address;

    public Profile() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
