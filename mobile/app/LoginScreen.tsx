import { Link } from "expo-router";
import { useState } from "react";
import { Button, StyleSheet, Text, TextInput, View } from "react-native";

type FormState = {
  email: string;
  password: string;
};

export default function LoginScreen() {

  const BACKEND_URL = process.env.EXPO_PUBLIC_BACKEND_URL;

  const [form, setForm] = useState<FormState>({
    email: "",
    password: ""
  });
  
  const [message, setMessage] = useState<string>("");

  const handleLogin = async () => {
    try {
      const res = await fetch(`${BACKEND_URL}/api/users/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(form),
      });
      const data = await res.json();
      setMessage(data.error ?? "Logged in!");
    } catch (err) {
      setMessage("Login failed");
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Login</Text>

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

      <Button title="Login" onPress={handleLogin} />

      {message ? <Text style={styles.message}>{message}</Text> : null}

      <Link href="/SignupScreen">Signup</Link>
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
    marginTop: 15,
    textAlign: "center",
    color: "red"
  }
});