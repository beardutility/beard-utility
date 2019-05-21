package it.thommibens.beard.utility.optionals;

import java.util.Optional;
import java.util.concurrent.CompletionException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;


/**
 * A container object that describe the result of an asynchronous execution,
 * which can have been completed normally or exceptionally.
 * It can contains an Optional of the possibly-null result value or
 * the exception thrown during the execution.
 * 
 * 
 * @author thommibens
 * 
 *
 * @param <T> type of resultValue
 */
public class OptionalCompletion<T> {

	private Optional<T> value;
	private Optional<CompletionException> ex;

	protected OptionalCompletion(T obj) {
		this.value = Optional.ofNullable(obj);
		this.ex = Optional.empty();
	}

	protected OptionalCompletion(CompletionException t) {
		this.value = Optional.empty();
		this.ex = Optional.of(t);
	}

	public static <T> OptionalCompletion<T> of(T obj) {
		return new OptionalCompletion<T>(obj);
	}

	public static <T> OptionalCompletion<T> of(CompletionException t) {
		return new OptionalCompletion<T>(t);
	}

	public static <T> OptionalCompletion<T> of(Throwable t) {
		return new OptionalCompletion<T>(new CompletionException(t));
	}

	/**
	 * If an exception is present return true, otherwise return false
	 * @return  true if an exception is present, otherwise return false
	 */
	public boolean completedExceptionally() {
		return ex.isPresent();
	}

	/**
	 * If an exception is present return false, otherwise return true
	 * @return false if an exception is present, otherwise return true
	 */
	public boolean completedNormally() {
		return !ex.isPresent();
	}

	public void ifCompletedExceptionally(Consumer<Throwable> consumer) {
		this.ex.map(CompletionException::getCause).ifPresent(consumer);
	}

	/**
	 * If the exception is present throws it wrapped by CompletionException,
	 * otherwise return an Optional describing the possibly-null result.
	 * @return  a possibly-null Optional that describe the execution result value
	 */
	public Optional<T> get() {
		if (completedExceptionally())
			throw this.ex.get();
		return this.value;
	}


	/**
	 * If the exception is present return the supplier Optional,
	 * otherwise return an Optional describing the possibly-null result.
	 * @param  the possibly-null value to be returned, if exception is present 
	 * @return a possibly-null Optional that describe the execution result value
	 */
	public Optional<T> orElse(Optional<T> supplier) {
		if (completedExceptionally())
			return supplier;
		return this.value;
	}

	/**
	 * If the exception is present return the supplier Optional,
	 * otherwise return an Optional describing the possibly-null result.
	 * @param  the possibly-null value to be returned, if exception is present 
	 * @return a possibly-null Optional that describe the execution result value
	 */
	public Optional<T> orElseGet(Supplier<Optional<T>> supplier) {
		if (completedExceptionally())
			return supplier.get();
		return this.value;
	}
	
	public Optional<T> orElseIf(Predicate<Throwable> predicate, Optional<T> supplier) {
		if (completedExceptionally()) {
			if( this.ex.map(CompletionException::getCause).filter(predicate).isPresent() )
				return supplier;
			throw this.ex.get();
		}
		return this.value;
	}

	public Optional<T> orElseGetIf(Predicate<Throwable> predicate, Supplier<Optional<T>> supplier) {
		if (completedExceptionally()) {
			if( this.ex.map(CompletionException::getCause).filter(predicate).isPresent() )
				return supplier.get();
			throw this.ex.get();
		}
		return this.value;
	}
	
}
