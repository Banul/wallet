package com.banulsoft.wallet

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class WalletTest extends Specification {

    def "context loads correctly"() {
        given:
        1 == 1

        when:
        1 == 1

        then:
        1 == 1
    }
}