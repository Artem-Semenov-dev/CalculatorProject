package src.calculator.fsm.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.identifier.IdentifierMachine;
import src.calculator.fsm.util.CharSequenceReader;
import src.calculator.fsm.util.ResolvingException;



class FunctionNameTransducer implements Transducer<FunctionHolder> {

    private static final Logger logger = LoggerFactory.getLogger(FunctionNameTransducer.class);

    @Override
    public boolean doTransition(CharSequenceReader inputChain, FunctionHolder outputChain) throws ResolvingException {

        StringBuilder stringBuilder = new StringBuilder();

        IdentifierMachine identifierMachine = IdentifierMachine.create();

        if (identifierMachine.run(inputChain, stringBuilder)){

            if (logger.isInfoEnabled()){

                logger.info("Function name - {}", stringBuilder);
            }

            outputChain.setFunctionName(stringBuilder.toString());
            return true;
        }

        return false;
    }

}
