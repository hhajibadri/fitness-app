import { useState } from "react";
import { View, TextInput, Button, Text, StyleSheet, Pressable } from "react-native";

import { DatePickerModal } from "react-native-paper-dates";
import { Ionicons } from "@expo/vector-icons";

type FormState = {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  dateOfBirth: string;
  gender: string;
};

export default function SignupScreen() {

  const BACKEND_URL = process.env.EXPO_PUBLIC_BACKEND_URL;

  const [form, setForm] = useState<FormState>({
    email: "",
    password: "",
    firstName: "",
    lastName: "",
    dateOfBirth: String((new Date()).toLocaleDateString()),
    gender: ""
  });

  const [passwordCheck, setPasswordCheck] = useState("");
  const [showDatePicker, setShowDatePicker] = useState(false);
  const [showGenderPicker, setShowGenderPicker] = useState(false);
  const [message, setMessage] = useState("");

  const isSame = form.password === passwordCheck;
  const formattedDob = form.dateOfBirth ? form.dateOfBirth : "Date of Birth";

  const handleSignup = async () => {
    try {
      const res = await fetch(`${BACKEND_URL}/api/users/register`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(form),
      });
      const data = await res.json();
      setMessage(data.error ?? "Account created!");
    } catch (err) {
      setMessage("Signup failed!");
    }
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Signup</Text>

      <TextInput
        style={styles.input}
        placeholder="Email"
        value={form.email}
        onChangeText={(text: string) => setForm({ ...form, email: text })}
        autoCapitalize="none"
        keyboardType="email-address"
      />

      <TextInput
        style={styles.input}
        placeholder="Password"
        value={form.password}
        onChangeText={(text: string) => setForm({ ...form, password: text })}
        secureTextEntry
      />

      <TextInput
        style={styles.input}
        placeholder="Verify password"
        value={passwordCheck}
        onChangeText={setPasswordCheck}
        secureTextEntry
      />

      {!isSame ? <Text style={styles.message}>Passwords do not match</Text> : null}

      <TextInput
        style={styles.input}
        placeholder="First name"
        value={form.firstName}
        onChangeText={(text: string) => setForm({ ...form, firstName: text })}
      />

      <TextInput
        style={styles.input}
        placeholder="Last name"
        value={form.lastName}
        onChangeText={(text: string) => setForm({ ...form, lastName: text })}
      />

      <Pressable
        style={styles.dobInput}
        onPress={() => setShowDatePicker(true)}
      >
        <Text>{formattedDob}</Text>
        <Ionicons name="calendar-outline" size={20} color="#666" />
      </Pressable>

      <Pressable
        style={styles.genderInput}
        onPress={() => setShowGenderPicker(!showGenderPicker)}
      >
        <Text>{form.gender || "Select Gender"}</Text>
        <Ionicons name="chevron-down-outline" size={20} color="#666" />

        {showGenderPicker && (
          <View>
            <Pressable
              style={styles.genderInputOption}
              onPress={() => {
                setForm({ ...form, gender: "MALE" });
                setShowGenderPicker(false);
              }}>
              <Text>MALE</Text>
            </Pressable>
            <Pressable
              style={styles.genderInputOption}
              onPress={() => {
                setForm({ ...form, gender: "FEMALE" });
                setShowGenderPicker(false);
              }}>
              <Text>FEMALE</Text>
            </Pressable>
            <Pressable
              style={styles.genderInputOption}
              onPress={() => {
                setForm({ ...form, gender: "UNSPECIFIED" });
                setShowGenderPicker(false);
              }}>
              <Text>UNSPECIFIED</Text>
            </Pressable>
          </View>
        )}

      </Pressable>

      {showDatePicker && (
        <DatePickerModal
          locale="en"
          mode="single"
          visible={showDatePicker}
          onDismiss={() => setShowDatePicker(false)}
          date={new Date(form.dateOfBirth) ?? undefined}
          onConfirm={({ date }) => {
            setShowDatePicker(false);
            if (date) {
              setForm({
                ...form,
                dateOfBirth: String(date.toLocaleDateString())
              });
            }
          }}
        />
      )}

      <Button title="Signup" onPress={handleSignup} disabled={!isSame || !form.password} />

      {message ? <Text style={styles.message}>{message}</Text> : null}

    </View>
  );

}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    paddingHorizontal: 20,
    backgroundColor: "#f7f7f7"
  },
  title: {
    fontSize: 28,
    fontWeight: "bold",
    marginBottom: 20,
    textAlign: "center"
  },
  input: {
    height: 50,
    borderColor: "#ccc",
    borderWidth: 1,
    borderRadius: 8,
    paddingHorizontal: 15,
    marginBottom: 15,
    backgroundColor: "#fff"
  },
  message: {
    marginVertical: 15,
    textAlign: "center",
    color: "red"
  },
  genderInput: {
    borderWidth: 1,
    borderRadius: 8,
    paddingHorizontal: 15,
    backgroundColor: "#fff",
    borderColor: "#ccc",
    justifyContent: "center",
    alignItems: "center",
    marginBottom: 15
  },
  genderInputOption: {
    alignItems: "center",
    justifyContent: "center",
    borderWidth: 1,
    borderRadius: 8,
    paddingHorizontal: 2,
    paddingVertical: 1,
    marginVertical: 4
  },
  dobInput: {
    height: 50,
    borderColor: "#ccc",
    borderWidth: 1,
    borderRadius: 8,
    paddingHorizontal: 15,
    marginBottom: 15,
    backgroundColor: "#fff",
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between"
  }
});