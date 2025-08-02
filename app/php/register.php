<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");

require_once 'config.php';

$username = $_POST['username'];
$password = md5($_POST['password']); // Note: MD5 is not secure for production

$sql = "SELECT * FROM users WHERE username = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $username);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    http_response_code(400);
    echo json_encode(["message" => "Username already exists"]);
} else {
    $sql = "INSERT INTO users (username, password) VALUES (?, ?)";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ss", $username, $password);
    
    if ($stmt->execute()) {
        echo json_encode(["username" => $username, "password" => $password]);
    } else {
        http_response_code(500);
        echo json_encode(["message" => "Registration failed"]);
    }
}

$stmt->close();
$conn->close();
?>