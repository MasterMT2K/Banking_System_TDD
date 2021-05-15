public class MasterControl {

    Bank bank;
    CreateCommandValidator createCommandValidator;
    DepositCommandValidator depositCommandValidator;
    CreateCommandProcessor createCommandProcessor;
    DepositCommandProcessor depositCommandProcessor;
    CommandStorage commandStorage;

    public MasterControl(Bank bank, CreateCommandValidator createCommandValidator, DepositCommandValidator depositCommandValidator, CreateCommandProcessor createCommandProcessor, DepositCommandProcessor depositCommandProcessor, CommandStorage commandStorage) {
        this.bank = bank;
        this.createCommandValidator = createCommandValidator;
        this.depositCommandValidator = depositCommandValidator;
        this.createCommandProcessor = createCommandProcessor;
        this.depositCommandProcessor = depositCommandProcessor;
        this.commandStorage = commandStorage;
    }

}
