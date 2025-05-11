package fr.dl11.openmedia.data.mapper;

import fr.dl11.openmedia.data.person.PersonData;
import fr.dl11.openmedia.data.person.PersonNewsData;
import fr.dl11.openmedia.datasource.data.DataRecord;
import fr.dl11.openmedia.datasource.data.FieldData;
import fr.dl11.openmedia.datasource.mappers.IDataMapper;

import java.time.Year;
import java.util.*;

/**
 * Maps data records to {@link PersonData} objects.
 *
 * <p>The {@code PersonMapper} class implements the {@link IDataMapper} interface
 * to map {@link DataRecord} objects into {@link PersonData} objects. It validates
 * the structure of the data record and extracts person-related information, including
 * yearly news data such as challenge ranks and Forbes ranks.
 */
public class PersonMapper implements IDataMapper<PersonData> {
    private final Collection<String> fields;
    private final String[] headlines = {
            "rangChallenges".toLowerCase(),
            "milliardaireForbes".toLowerCase(),
    };

    /**
     * Generates a collection of news headers for a specific year.
     *
     * @param year The year for which to generate news headers.
     * @return A collection of news headers for the given year.
     */
    private Collection<String> getNewsHeadersByYear(int year) {
        return Arrays.stream(headlines)
                .map(headline -> headline + year)
                .toList();
    }

    /**
     * Constructs a new {@code PersonMapper}.
     *
     * <p>Initializes the list of expected fields by combining static fields
     * with dynamically generated news headers for the years 2021 to 2024.
     */
    public PersonMapper() {
        List<String> headers = new ArrayList<>(List.of("nom"));
        headers.addAll(getNewsHeadersByYear(2024));
        headers.addAll(getNewsHeadersByYear(2023));
        headers.addAll(getNewsHeadersByYear(2022));
        headers.addAll(getNewsHeadersByYear(2021));

        fields = Collections.unmodifiableList(headers);
    }

    /**
     * Checks if the given data record can be mapped to a {@link PersonData} object.
     *
     * @param record The data record to validate.
     * @return {@code true} if the record can be mapped, {@code false} otherwise.
     */
    @Override
    public boolean canMap(DataRecord record) {
        return record != null && record.fields() != null
                && record.fields().size() == fields.size()
                && record.fields().stream().allMatch(field -> fields.contains(field.name().toLowerCase()));
    }

    /**
     * Maps a data record to a {@link PersonData} object.
     *
     * <p>Extracts the person's name and yearly news data from the record. If the record
     * contains unknown fields or lacks a person's name, a {@link MatchException} is thrown.
     *
     * @param record The data record to map.
     * @return A {@link PersonData} object containing the mapped data.
     * @throws MatchException If the record contains invalid or missing data.
     */
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

        return personData;
    }
}