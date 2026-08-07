Set-Location 'M:/OTT-Platform'
$p = Start-Process -NoNewWindow -FilePath 'java' -ArgumentList '-jar','target/flowflix-0.0.1-SNAPSHOT.jar' -WorkingDirectory 'M:/OTT-Platform' -PassThru
Write-Host "Started PID $($p.Id)"
for ($i=0; $i -lt 60; $i++) {
    try {
        $r = Invoke-RestMethod -Uri 'http://127.0.0.1:8081/health' -Method Get -TimeoutSec 2
        Write-Host 'HEALTH_OK'
        break
    } catch {
        Start-Sleep -Seconds 1
    }
}
