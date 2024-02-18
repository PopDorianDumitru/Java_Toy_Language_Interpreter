package exceptions;

import repository.Repository;

public class RepositoryException extends MyException{
    public RepositoryException(String msg){
        super(msg);
    }
}
