# Portfolio outbox module

Simple implementation of outbox pattern. When we need to start
tracking company value we just send request via kafka to SDS (stock data service).

Requested comapny is registered in SDS as the one we are interested in, so
we will periodically be getting information about companies we have registered
on proper kafka topic.