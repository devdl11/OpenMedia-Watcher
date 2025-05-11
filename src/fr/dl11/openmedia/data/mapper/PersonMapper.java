package fr.dl11.openmedia.data.mapper;

import fr.dl11.openmedia.data.persons.PersonData;
import fr.dl11.openmedia.data.persons.PersonNewsData;
import fr.dl11.openmedia.datasource.data.DataRecord;
import fr.dl11.openmedia.datasource.data.FieldData;
import fr.dl11.openmedia.datasource.mappers.IDataMapper;

import java.lang.reflect.Array;
import java.time.Year;
import java.util.*;

public class PersonMapper implements IDataMapper<PersonData> {
    private final Collection<String> fields;
    private final String[] headlines = {
            "rangChallenges".toLowerCase(),
            "milliardaireForbes".toLowerCase(),
    };

    private Collection<String> getNewsHeadersByYear(int year) {
        return Arrays.stream(headlines)
                .map(headline -> headline + year)
                .toList();
    }

    public PersonMapper() {
        List<String> headers = new ArrayList<>(List.of("nom"));
        headers.addAll(getNewsHeadersByYear(2024));
        headers.addAll(getNewsHeadersByYear(2023));
        headers.addAll(getNewsHeadersByYear(2022));
        headers.addAll(getNewsHeadersByYear(2021));

        fields = Collections.unmodifiableList(headers);
    }

    @Override
    public boolean canMap(DataRecord record) {
        return record != null && record.fields() != null
                && record.fields().size() == fields.size()
                && record.fields().stream().allMatch(field -> fields.contains(field.name().toLowerCase()));
    }

    @Override
    public PersonData mapData(DataRecord record) throws MatchException {
        assert record != null && canMap(record);

        HashMap<Integer, PersonNewsData> newsData = new HashMap<>();
        String personName = null;
        for (FieldData fieldData : record.fields()) {
            if (fieldData.data().isEmpty()) continue;
            String fieldName = fieldData.name().toLowerCase();

            if (fieldName.equals("nom")) {
                personName = fieldData.data();
                continue;
            }

            for (String headline : headlines) {
                if (fieldName.startsWith(headline)) {
                    int year = Integer.parseInt(fieldName.substring(headline.length()));
                    newsData
                            .computeIfAbsent(year, k -> new PersonNewsData(Year.of(year)));
                    if (fieldName.startsWith(headlines[0])) {
                        newsData.get(year).addChallengeRank(Integer.parseInt(fieldData.data()));
                    } else if (fieldName.startsWith(headlines[1])) {
                        newsData.get(year).addForbesRank(Integer.parseInt(fieldData.data()));
                    } else {
                        throw new MatchException("Unknown field name: " + fieldName, new Throwable(fieldName));
                    }
                    break;
                }
            }
        }

        if (personName == null) {
            throw new MatchException("No person found", new Throwable("No person found"));
        }

        PersonData personData = new PersonData(personName);
        for (Map.Entry<Integer, PersonNewsData> entry : newsData.entrySet()) {
            personData.addNewsData(entry.getValue());
        }

        System.out.println(personData);

        return personData;
    }
}
