package persistence;

import org.json.JSONObject;

// REFERENCE: Taken from UBC CPSC210's Workroom App's Writable class
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}