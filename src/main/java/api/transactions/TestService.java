
package api.transactions;

import javax.inject.Inject;
import javax.transaction.Transactional;

public class TestService implements TestServiceIface {

    private final TestRepository repository;

    @Inject
    public TestService(TestRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void addNewUser(String name, String email) {
        long userId = repository.addUser(name);

        repository.addEmail(userId, email);

    }
}
