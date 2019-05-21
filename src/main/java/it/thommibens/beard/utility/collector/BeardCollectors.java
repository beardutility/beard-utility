package it.thommibens.beard.utility.collector;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.thommibens.beard.utility.optionals.OptionalCompletion;

public class BeardCollectors {
	 
    public static <T> Collector<CompletableFuture<T>,?,Stream<OptionalCompletion<T>>> collectThenWaitAndStream() {
        return Collectors.collectingAndThen(Collectors.toList(),(list) ->
        {
            @SuppressWarnings("rawtypes")
            CompletableFuture[] arr = new  CompletableFuture[list.size()];
            list.toArray(arr);
           
            try {
                CompletableFuture.allOf(arr).join();
            } catch (CompletionException e) {
                // continue because exception
                //will be managed fore each single future
            }
                      
            return list.stream().map(f -> {
                try {
                    return OptionalCompletion.of(f.get());
                } catch (Throwable t) {
                    return OptionalCompletion.of(t);
                }
            });
        });
    }
   
}