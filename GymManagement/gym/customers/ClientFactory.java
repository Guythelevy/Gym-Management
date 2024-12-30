package gym.customers;
import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InvalidAgeException;
import gym.shared.Person;

import java.util.List;


public class ClientFactory {
    public static Client createClient(Person person, int currentSecretaryId, int requestingSecretaryId, List<Client> clients)
            throws InvalidAgeException, DuplicateClientException {


        if (currentSecretaryId != requestingSecretaryId) {
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions");
        }


        for (Client existingClient : clients) {
            if (existingClient.getName().equals(person.getName())) {
                throw new DuplicateClientException("Error: The client is already registered");
            }
        }


        if (person.getAge() < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
        }


        Client newClient = new Client(person);

        return newClient;
    }



    public static boolean canUnregisterClient(Client client, int currentSecretaryId, int requestingSecretaryId, List<Client> clients)
            throws ClientNotRegisteredException, IllegalStateException {


        if (currentSecretaryId != requestingSecretaryId) {
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions");
        }


        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }


        if (!clients.contains(client)) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }


        return true;
    }

}
