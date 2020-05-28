package io.emeraldpay.pjc.json

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class SystemHealthJsonSpec extends Specification {

    ObjectMapper objectMapper = JsonSpecCommons.objectMapper

    def "Deserialize"() {
        setup:
        InputStream json = BlockJsonSpec.classLoader.getResourceAsStream("system/health.json")
        when:
        def act = objectMapper.readValue(json, SystemHealthJson.class)
        then:
        act != null
        !act.syncing
        act.peers == 25
        act.shouldHavePeers
    }

    def "Same data are equal"() {
        setup:
        String json = BlockJsonSpec.classLoader.getResourceAsStream("system/health.json").text
        when:
        def x = objectMapper.readValue(json, SystemHealthJson.class)
        def y = objectMapper.readValue(json, SystemHealthJson.class)
        then:
        x == y
        x.hashCode() == y.hashCode()
    }

    def "Diff data are not equal"() {
        setup:
        String json = BlockJsonSpec.classLoader.getResourceAsStream("system/health.json").text
        when:
        def x = objectMapper.readValue(json, SystemHealthJson.class)
        def y = objectMapper.readValue(json, SystemHealthJson.class).tap {
            peers++
        }
        then:
        x != y
    }
}
