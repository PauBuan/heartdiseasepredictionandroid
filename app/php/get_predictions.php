<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");

require_once 'config.php';

$username = $_GET['username'];

$sql = "SELECT * FROM predictions WHERE patient = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $username);
$stmt->execute();
$result = $stmt->get_result();

$predictions = [];
while ($row = $result->fetch_assoc()) {
    $predictions[] = [
        "patient" => $row['Patient'],
        "age" => (int)$row['Age'],
        "sex" => $row['Sex'],
        "chestPainType" => $row['Chest Pain Type'],
        "cholesterol" => (int)$row['Cholesterol'],
        "maxHeartRate" => (int)$row['Max Heart Rate'],
        "oldPeak" => (float)$row['OldPeak'],
        "majorVessels" => (int)$row['Major Vessels'],
        "thalassemia" => $row['Thalassemia'],
        "model" => $row['Model'],
        "predictedCluster" => $row['Predicted Cluster']
    ];
}

echo json_encode($predictions);

$stmt->close();
$conn->close();
?>