package ru.dreamkas.utils.time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class PatternTest {
    static final LocalDate DECEMBER_31 = LocalDate.of(2016, Month.DECEMBER, 31);
    static final LocalDate JAN_2 = LocalDate.of(2016, Month.JANUARY, 2);
    static final LocalTime NIGHT = LocalTime.of(1, 2, 3).with(ChronoField.MILLI_OF_SECOND, 23);
    static final LocalTime DAY = LocalTime.of(13, 23, 34).with(ChronoField.MILLI_OF_SECOND, 123);
    static final ZoneId UTC_PLUS_10 = ZoneId.of("Etc/GMT-10");
    static final ZoneId UTC_MINUS_3 = ZoneId.of("Etc/GMT+3");
    static final ZonedDateTime DECEMBER_31_NIGHT_UTC_PLUS_10 = ZonedDateTime.of(DECEMBER_31, NIGHT, UTC_PLUS_10);
    static final ZonedDateTime JAN_2_DAY_UTC_MINUS_3 = ZonedDateTime.of(JAN_2, DAY, UTC_MINUS_3);

    @DisplayName("2016-12-31 01:02:03 +1000: двузначные день и месяца, однозначные час, минута, секунда; двузначный часовой пояс +")
    @TestFactory
    public Collection<DynamicTest> checkPatterns1() {
        Map<Pattern, String> cases = new HashMap<>();
        cases.put(Pattern.DDMMYYYY, "31122016");
        cases.put(Pattern.DDMMYY, "311216");
        cases.put(Pattern.DDMMYYHHmm, "3112160102");
        cases.put(Pattern.DDMMYYHHmmss, "311216010203");
        cases.put(Pattern.DDMMYYYY_dash, "31-12-2016");
        cases.put(Pattern.YYYYMMDD_dash, "2016-12-31");
        cases.put(Pattern.DDMMYYYY_dots, "31.12.2016");
        cases.put(Pattern.DDMMYYYY_HHmm_dots_colon, "31.12.2016 01:02");
        cases.put(Pattern.DDMMYYYY_HHmmss_dash_colon, "31-12-2016 01:02:03");
        cases.put(Pattern.DDMMYYYY_HHmmss_dots_colon, "31.12.2016 01:02:03");
        cases.put(Pattern.DDMMYYYY_Hmmss_dots_colon, "31.12.2016 1:02:03");
        cases.put(Pattern.HHmm_colon, "01:02");
        cases.put(Pattern.HHmmss_colon, "01:02:03");
        cases.put(Pattern.Hmm_colon, "1:02");
        cases.put(Pattern.Hmmss_colon, "1:02:03");
        cases.put(Pattern.HHmmss, "010203");
        cases.put(Pattern.YYYYMMDD_HHmmss_dash_colon, "2016-12-31 01:02:03");
        cases.put(Pattern.YYYYMMDD_HHmmss_dots_dash_colons, "2016.12.31-01:02:03");
        cases.put(Pattern.YYYYMMDD_HHmmss_Z_dash_colon, "2016-12-31 01:02:03 +1000");
        cases.put(Pattern.YYYYMMDD_HHmmssSSS_Z_dash_colon, "2016-12-31 01:02:03.023 +1000");
        cases.put(Pattern.YYYYMMDD, "20161231");
        cases.put(Pattern.YYYYMMDD_HHmm_dash_colon, "2016-12-31 01:02");
        cases.put(Pattern.YYYYMMDDHHmmss, "20161231010203");
        cases.put(Pattern.YYYYMMDDHHmm_T, "20161231T0102");
        cases.put(Pattern.YYMMDDHHMM, "1612310102");

        return makeTests(DECEMBER_31_NIGHT_UTC_PLUS_10, cases);
    }

    @DisplayName("2016-01-02 13:23:34 -0300: однозначные день и месяца, двузначные час, минута, секунда; однозначный часовой пояс ")
    @TestFactory
    public Collection<DynamicTest> checkPatterns2() {
        Map<Pattern, String> cases = new HashMap<>();
        cases.put(Pattern.DDMMYYYY, "02012016");
        cases.put(Pattern.DDMMYY, "020116");
        cases.put(Pattern.DDMMYYHHmm, "0201161323");
        cases.put(Pattern.DDMMYYHHmmss, "020116132334");
        cases.put(Pattern.DDMMYYYY_dash, "02-01-2016");
        cases.put(Pattern.YYYYMMDD_dash, "2016-01-02");
        cases.put(Pattern.DDMMYYYY_dots, "02.01.2016");
        cases.put(Pattern.DDMMYYYY_HHmm_dots_colon, "02.01.2016 13:23");
        cases.put(Pattern.DDMMYYYY_HHmmss_dash_colon, "02-01-2016 13:23:34");
        cases.put(Pattern.DDMMYYYY_HHmmss_dots_colon, "02.01.2016 13:23:34");
        cases.put(Pattern.DDMMYYYY_Hmmss_dots_colon, "02.01.2016 13:23:34");
        cases.put(Pattern.HHmm_colon, "13:23");
        cases.put(Pattern.HHmmss_colon, "13:23:34");
        cases.put(Pattern.Hmm_colon, "13:23");
        cases.put(Pattern.Hmmss_colon, "13:23:34");
        cases.put(Pattern.HHmmss, "132334");
        cases.put(Pattern.YYYYMMDD_HHmmss_dash_colon, "2016-01-02 13:23:34");
        cases.put(Pattern.YYYYMMDD_HHmmss_dots_dash_colons, "2016.01.02-13:23:34");
        cases.put(Pattern.YYYYMMDD_HHmmss_Z_dash_colon, "2016-01-02 13:23:34 -0300");
        cases.put(Pattern.YYYYMMDD_HHmmssSSS_Z_dash_colon, "2016-01-02 13:23:34.123 -0300");
        cases.put(Pattern.YYYYMMDD, "20160102");
        cases.put(Pattern.YYYYMMDD_HHmm_dash_colon, "2016-01-02 13:23");
        cases.put(Pattern.YYYYMMDDHHmmss, "20160102132334");
        cases.put(Pattern.YYYYMMDDHHmm_T, "20160102T1323");
        cases.put(Pattern.YYMMDDHHMM, "1601021323");

        return makeTests(JAN_2_DAY_UTC_MINUS_3, cases);
    }

    private List<DynamicTest> makeTests(ZonedDateTime date, Map<Pattern, String> cases) {
        checkAllPatternsTested(date, cases);
        return cases.entrySet().stream()
            .map(e -> DynamicTest.dynamicTest(e.getKey().name(), () -> test(e, date)))
            .collect(Collectors.toList());
    }

    private void checkAllPatternsTested(ZonedDateTime date, Map<Pattern, String> cases) {
        Set<Pattern> all = Arrays.stream(Pattern.values()).collect(Collectors.toCollection(TreeSet::new));
        Set<Pattern> tested = new TreeSet<>(cases.keySet());
        Assertions.assertIterableEquals(all, tested, () -> {
            Set<Pattern> notTested = new TreeSet<>(all);
            notTested.removeAll(tested);
            return "Шаблоны " + notTested + " не покрыты покрыты тестами для даты " + date;
        });
    }

    private void test(Map.Entry<Pattern, String> e, ZonedDateTime of) {
        Assertions.assertEquals(e.getValue(), of.format(e.getKey().formatter()));
    }
}