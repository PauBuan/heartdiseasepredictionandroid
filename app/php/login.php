<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");

require_once 'config.php';

$username = $_POST['username'];
$password = md5($_POST['password']); // Note: MD5 is not secure for production

$sql = "SELECT * FROM users WHERE username = ? AND password = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("ss", $username, $password);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $user = $result->fetch_assoc();
    echo json_encode(["username" => $user['username'], "password" => $user['password']]);
} else {
    http_response_code(401);
    echo json_encode(["message" => "Invalid credentials"]);
}

$stmt->close();
$conn->close();
?>
