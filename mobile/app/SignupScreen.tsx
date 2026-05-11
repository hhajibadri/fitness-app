import { useState } from "react";
import { View, TextInput, Button, Text, StyleSheet, Pressable } from "react-native";

import DateTimePicker from "@react-native-community/datetimepicker";
import { Ionicons } from "@expo/vector-icons";

type FormState = {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  dob: Date | null;
};

export default function SignupScreen() {

  const BACKEND_URL = process.env.EXPO_PUBLIC_BACKEND_URL;

  const [form, setForm] = useState<FormState>({
    email: "",
    password: "",
    firstName: "",
    lastName: "",
    dob: null
  });

  const [passwordCheck, setPasswordCheck] = useState<string>("");
  const [showPicker, setShowPicker] = useState<boolean>(false);
  const [message, setMessage] = useState<string>("");

  const isSame = form.password === passwordCheck;
  const formattedDob = form.dob ? form.dob.toLocaleDateString() : "Date of Birth";

  const handleSignup = async () => {
    try {
      const res = await fetch(`${BACKEND_URL}/api/users/signup`, {
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
        onPress={() => setShowPicker(true)}
      >
        <Text>{formattedDob}</Text>
        <Ionicons name="calendar-outline" size={20} color="#666" />
      </Pressable>

      {showPicker && (
        <DateTimePicker
          value={form.dob ?? new Date()}
          mode="date"
          display="default"
          maximumDate={new Date()}
          onChange={(_, selectedDate) => {
            setShowPicker(false);
            if (selectedDate) {
              setForm({...form, dob: selectedDate});
            }
          }}
        />
      )}

      <Button title="Signup" onPress={handleSignup} disabled={!isSame || !form.password} />

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