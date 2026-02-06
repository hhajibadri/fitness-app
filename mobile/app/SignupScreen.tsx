import { useState } from "react";
import { View, TextInput, Button, Text, StyleSheet, Pressable } from "react-native";

import DateTimePicker from "@react-native-community/datetimepicker";
import { Ionicons } from "@expo/vector-icons";

export default function SignupScreen() {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordCheck, setPasswordCheck] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");

  const [dob, setDob] = useState<Date | null>(null);
  const [showPicker, setShowPicker] = useState(false);

  const isSame = password === passwordCheck;
  const formattedDob = dob ? dob.toLocaleDateString() : "Date of Birth";

  const handleSignup = async () => {
    return;
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Signup</Text>

      <TextInput
        style={styles.input}
        placeholder="Email"
        value={email}
        onChangeText={setEmail}
        autoCapitalize="none"
        keyboardType="email-address"
      />

      <TextInput
        style={styles.input}
        placeholder="Password"
        value={password}
        onChangeText={setPassword}
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
        value={firstName}
        onChangeText={setFirstName}
      />

      <TextInput
        style={styles.input}
        placeholder="Last name"
        value={lastName}
        onChangeText={setLastName}
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
          value={dob ?? new Date()}
          mode="date"
          display="default"
          maximumDate={new Date()}
          onChange={(_, selectedDate) => {
            setShowPicker(false);
            if (selectedDate) {
              setDob(selectedDate);
            }
          }}
        />
      )}

      <Button title="Signup" onPress={handleSignup} disabled={!isSame || !password} />

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