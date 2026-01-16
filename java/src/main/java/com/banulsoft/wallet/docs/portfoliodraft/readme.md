# Portfolio draft module

This module is responsible to get request from user and then deciding
whether we know that what he defined as bought stocks are valid or we need to check it.

For example if user defined Amazon we are already tracking it. So there
is no need to send request to SDS (stock data service).

But if user provides some not very common company it is possible that
we are not having information whether it exists so we need to check it in SDS.